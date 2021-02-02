package test;

import model.Translation;

public class Test {
	public static void main() {
		Test.print(Translation.translate("prova"));
		Test.print(Translation.translate("prova 12"));
		Test.print(Translation.translate("prova 12 prova"));
		Test.print(Translation.translate("a 12 b 15"));
		Test.print(Translation.translate("cancellazione gratuita fino a 7 giorni"));
		Test.print(Translation.translate("tutto l'anno"));
		Test.print(Translation.translate("dal 1 marzo al 31 dicembre 2021"));
	}

	private static void print(final String[] strings) {
		if (strings == null) {
			return;
		}

		for (final String string : strings) {
			System.out.println(string);
		}
		System.out.println();
	}
}
