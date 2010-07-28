
import euler.Euler;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author snadi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Euler engine = new Euler();
      
        engine.load("facts.n3");
        engine.load("rules.n3");
//        engine.load("originalfiles/t10.n3");
//        engine.load("originalfiles/t10a.n3");
//        engine.load("originalfiles/foo.n3");

        System.out.println(engine.proof("query.n3"));

    }
}
