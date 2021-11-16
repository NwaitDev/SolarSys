package jrubytrying ;

import org.jruby.embed.LocalVariableBehavior ;
import org.jruby.embed.PathType ; import org.jruby.embed.ScriptingContainer ;

public class UsingAnimals {
    //
    private static void warn(String message) {
	String ctrl = " ** " ; System.out.println(ctrl + message + ctrl) ;
    }
    //
    public static void main(String[] args) {
	if (args.length == 0) {
	    String[] ruby_statement_a = {
		"puts $kingkong.get_name + ' is here!'", 
		"$kingkong.skullisland", "d = Dog.new('Rintintin')",
		"puts 'I am ' + d.get_name", "d.eat()", "d.bark()",
                "y = 2019", "puts %Q(We r in #{y}, wonderful!)",
                "puts %q[Waiting 4 retirement, good!]"
	    } ;
	    ScriptingContainer
		container =
		new ScriptingContainer(LocalVariableBehavior.PERSISTENT) ;
	    container.runScriptlet
                (PathType.ABSOLUTE,
                 "/Users/hufflen/programs/Ruby/JRuby/lc-9.rb") ;
	    warn("Beginning of the movie!") ;
	    for (String ruby_statement_s : ruby_statement_a) 
		container.runScriptlet(ruby_statement_s) ;
	    warn("The end.") ;
	} else System.out.println("Improper use!") ;
    }
    //
}

