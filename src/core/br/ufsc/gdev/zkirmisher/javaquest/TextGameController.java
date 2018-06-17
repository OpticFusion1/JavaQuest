package br.ufsc.gdev.zkirmisher.javaquest;


import static java.lang.Math.max;
import java.util.Random;

import br.ufsc.gdev.zkirmisher.javaquest.entities.*;
import br.ufsc.gdev.zkirmisher.javaquest.entities.Character;


/**
 * Controls game logic.
 */
public class TextGameController {

	// CONSTANTS
	private static final int INTENT_NOT_FOUND = -1;
	private static final int INTENT_HELP = 0;
	private static final int INTENT_MOVE = 1;
	private static final int INTENT_CHARACTER = 2;
	private static final int INTENT_INVENTORY = 3;
	private static final int INTENT_EQUIPMENT = 4;
	private static final int INTENT_ITEM_USE = 5;
	private static final int INTENT_UNEQUIP = 6;
	private static final int INTENT_ITEM_GET = 7;
	private static final int INTENT_BATTLE = 8;
	private static final int INTENT_SPEND_AP = 9;


	// ATTRIBUTES
	private TextGame game;
	private TextGameView view;

	private boolean wantsToExit = false;


	// CONSTRUCTORS
	public TextGameController(
		final TextGame game,
		final TextGameView view
	) {
		this.game = game;
		this.view = view;
	}


	// METHODS
	/**
	 * Runs the Game.
	 */
	public void start() {
		while (!wantsToExit && !game.hasLost()) {
			view.show(game.getCurrentRoom());
			if (game.hasWon()) break;
			handleCommand(view.getUserCommand().toLowerCase());
		}

		view.bye();
	}


	// PRIVATE FUNCTIONS
	/**
	 * Parse & send to execution
	 */
	private void handleCommand(final String command) {
		if (command.contains("sair")) {
			wantsToExit = true;

		} else if (command.contains("ajud")) {
			executeCommand(INTENT_HELP, command);

		} else if (command.contains("persona")) {
			executeCommand(INTENT_CHARACTER, command);

		} else if (command.contains("itens") || command.contains("inventorio") || command.contains("inventório")) {
			executeCommand(INTENT_INVENTORY, command);

		} else if (command.matches("desequipar \\d{1,}")) {
			executeCommand(INTENT_UNEQUIP, Integer.parseInt(command.substring(11)));

		} else if (command.contains("equip")) {
			executeCommand(INTENT_EQUIPMENT, command);

		} else if (command.contains("norte")) {
			executeCommand(INTENT_MOVE, Room.NORTH);

		} else if (command.contains("leste")) {
			executeCommand(INTENT_MOVE, Room.EAST);

		} else if (command.contains("sul")) {
			executeCommand(INTENT_MOVE, Room.SOUTH);

		} else if (command.contains("oeste")) {
			executeCommand(INTENT_MOVE, Room.WEST);

		} else if (command.matches("pegar \\d{1,}")) {
			executeCommand(INTENT_ITEM_GET, Integer.parseInt(command.substring(6)));

		} else if (command.matches("usar \\d{1,}")) {
			executeCommand(INTENT_ITEM_USE, Integer.parseInt(command.substring(5)));

		} else if (command.contains("atacar") || command.contains("lutar") || command.contains("bater")) {
			executeCommand(INTENT_BATTLE, command);

		} else if (command.matches("aumentar [a-z]{3}")) {
			executeCommand(INTENT_SPEND_AP, command.substring(9, 12).toUpperCase());

		} else {
			executeCommand(INTENT_NOT_FOUND, command);
		}
	}

	private void executeCommand(int intention, Object action) {
		switch (intention) {
			case INTENT_HELP:
				view.showMessage(
					"- Mova-se utilizando os nomes dos pontos cardeais. Ex: \"corra para o leste\".\n" +
					"- Para pegar um item do chão, digite \"pegar x\", sendo x o [índice] do item indicado pelo jogo.\n" +
					"- Digite \"inventório\" para mostrar os seus itens.\n" +
					"- Para usar um item, digite \"usar x\", sendo x o [índice] do item mostrado no seu inventório.\n" +
					"- Informações sobre o seu personagem são mostradas com o comando \"personagem\".\n" +
					"- Digite \"equipamentos\" para visualizar os seus equipamentos.\n" +
					"- Para desequipar um item, digite \"desequipar x\", sendo x o [índice] do equipamento.\n" +
					"- Se quiser desistir, digite \"sair\".");
				break;

			case INTENT_CHARACTER:
				view.showMessage(game.getPlayer().toString());
				break;

			case INTENT_INVENTORY:
				if (game.getPlayer().inventory().isEmpty()) {
					view.showMessage("Seu inventório está vazio.");
				} else {
					view.showMessage(game.getPlayer().inventory().toString());
				}
				break;

			case INTENT_EQUIPMENT:
				String equips = "";

				for (int e = 0; e < Item.EQUIP_SUBTYPES; ++e) {
					Equip equip = game.getPlayer().getEquipAt(e);
					if (equip == null) {
						continue;
					} else {
						equips += String.format("[%d] %s\n%s\n", e, equip.name(), equip.description());
					}
				}

				if (equips.equals("")) {
					view.showMessage("Não há nada equipado.");
				} else {
					view.showMessage(equips);
				}
				break;

			case INTENT_MOVE:
				if (game.getCurrentRoom().getOccupant() != null) {
					view.showMessage("Tem monstros por perto!");
				} else if (!game.goToAdjacent((int) action)) {
					view.showMessage("Você não pode ir para lá.");
				}
				break;

			case INTENT_ITEM_GET:
				try {
					Item item = game.getCurrentRoom().loot().get((int) action);

					if (item == null) {
						throw new NullPointerException();
					}

					if (game.getPlayer().inventory().add(item)) {
						view.showMessage(item.name() + " foi adicionado ao inventório.");
						game.getCurrentRoom().loot().remove((int) action, 1);
					} else {
						view.showMessage("Seu inventório já está cheio!");
					}

				} catch (Exception e) {
					view.showMessage("Item não encontrado.");
				}
				break;

			case INTENT_ITEM_USE:
				try {
					Item item = game.getPlayer().inventory().get((int) action);

					if (item == null) {
						throw new NullPointerException();
					}

					if (item instanceof Equip && !game.getPlayer().canEquip((Equip) item)) {
						view.showMessage("Você não possui os requisitos necessários para equipar " + item.name() + ".");
					} else {
						game.getPlayer().use((int) action);
						view.showMessage(item.name() + " usado com sucesso!");
					}

				} catch (Exception e) {
					view.showMessage(e.getMessage());//FIXME using equips throws exception
					view.showMessage("Esse item não está no seu inventório.");
				}
				break;

			case INTENT_UNEQUIP:
				try {
					Equip equip = game.getPlayer().getEquipAt((int) action);

					if (equip == null) {
						throw new NullPointerException();
					}

					game.getPlayer().unequip((int) action);
					view.showMessage(equip.name() + " foi desequipado e colocado no inventório.");

				} catch (Exception e) {
					view.showMessage("Equipamento não encontrado.");
				}
				break;

			case INTENT_BATTLE:
				if (game.getCurrentRoom().getOccupant() == null) {
					view.showMessage("Mas não há ninguém aqui...");
				} else {
					battle(game.getPlayer(), game.getCurrentRoom().getOccupant());
				}
				break;

			case INTENT_SPEND_AP:
				try {
					if (game.getPlayer().spendAP((String) action)) {
						view.showMessage("Você gastou 1 AP.");
					} else {
						view.showMessage("Você não tem mais pontos de atributo para gastar.");
					}
				} catch (Exception e) {
					view.showMessage(e.getMessage());
				}
				break;

			case INTENT_NOT_FOUND: default:
				view.showMessage("Não entendi o que você disse. Tente pedir \"ajuda\".");
				break;
		}
	}

	private void battle(Player player, Character enemy) {
		//First Strike is calculated based on dexterity
		if (player.dexterity() > enemy.dexterity()) {
			enemy.wound(calculateDamage(player, enemy));
			if (!enemy.isDead()) {
				player.wound(calculateDamage(enemy, player));
			}

		} else if (player.dexterity() < enemy.dexterity()) {
			player.wound(calculateDamage(enemy, player));
			if (!player.isDead()) {
				enemy.wound(calculateDamage(player, enemy));
			}

		} else {
			enemy.wound(calculateDamage(player, enemy));
			player.wound(calculateDamage(enemy, player));
		}

		if (player.isDead()) {
			view.showMessage("Você morreu!");
			return;
		}

		if (enemy.isDead()) {
			view.showMessage(enemy.name() + " está morto!");
			game.getCurrentRoom().setOccupant(null); //TODO monster drops equipments

			int level = player.level();
			player.gainExperience(enemy.experience());

			if (player.level() != level) {
				view.showMessage("Level Up!\nDigite \"aumentar ATT\" para gastar seus novos pontos de atributo (AP).");
			}
		}
	}

	private int calculateDamage(final Character attacker, final Character target) {
		String description = attacker.name();

		//choose damage source and reduction
		int source, reduction;
		if (attacker.spellDamage() >= attacker.attackDamage() && attacker.magic() >= 30) {
			source = attacker.spellDamage();
			reduction = target.negation();
			attacker.deplete(30);
			description += " joga uma bola de fogo em ";

		} else {
			source = attacker.attackDamage();
			reduction = target.armour();
			description += " ataca violentamente ";
		}

		description += target.name() + ", causando ";

		//critical damage increase
		Random rand = new Random();
		int crit = (rand.nextInt(100000 - 1) + 1 <= attacker.critical()) ? 2 : 1;
		source *= crit;//NOTE: critical is applied before damage reduction

		//damage calculation NOTE: damage reduction can't reduce damage to less than 20%
		int damage = max(source - reduction, (int) (source * 0.2));

		description += damage + " de dano.";

		view.showMessage(description);
		return damage;
	}

}
