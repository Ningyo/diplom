import de.tudarmstadt.ukp.lmf.api.Uby;
import de.tudarmstadt.ukp.lmf.model.core.LexicalEntry;
import de.tudarmstadt.ukp.lmf.model.core.Lexicon;
import de.tudarmstadt.ukp.lmf.model.morphology.Component;
import de.tudarmstadt.ukp.lmf.transform.DBConfig;

import java.util.List;

public class Main {
    public static void main(String[] args){
//        DBConfig db = new DBConfig("localhost:5432/uby","org.postgresql.Driver","postgresql","admin", "admin", false);
        DBConfig db = new DBConfig("localhost:3306/uby_open_0_7_0","com.mysql.jdbc.Driver","mysql","admin", "admin", false);
        Uby uby = new Uby(db);
        Lexicon lex = uby.getLexiconByName("FrameNet");
        List<LexicalEntry> entries = uby.getLexicalEntries("carry out", null, lex);
        LexicalEntry e = entries.get(0);
        String lemma = e.getLemmaForm(); // lemma
        String def = e.getSenses().get(0).getDefinitionText(); //definition
        System.out.println("lemma:" + lemma + " ; definition: " + def);
        if (e.getListOfComponents()!=null) {
            List<Component> loc = e.getListOfComponents().getComponents();
            System.out.println("\n-- Component of Multiword Lexemes --");
            System.out.println("Definition of '" + lemma + "': " + def);
            for (Component c : loc) {
                String constituentLemma = c.getTargetLexicalEntry().getLemmaForm();
                Boolean isHead = c.isHead();
                Boolean breakBefore = c.isBreakBefore();
                int position = c.getPosition();
                System.out.println(" lemma: " + constituentLemma + "\n  head: " +
                        isHead + "\n  breakbefore: " + breakBefore + "\n  position: " + position);
            }
        }
    }


}
