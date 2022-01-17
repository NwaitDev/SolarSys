//  ===========================================================================
//  Handling types suitable for Ruby in Java classes --- Seminex, 15th May 2014
//  rev. Nov. 2019 w.r.t. new constructor emptyList()

package jrubytrying ;

import org.jruby.javasupport.JavaEmbedUtils ; import org.jruby.Ruby ;
import org.jruby.RubyArray ; import org.jruby.RubyString ;
import java.util.Collections ;

public class Imported_By_JRuby {
    public static RubyArray make() {
        //  "..._jsja": Java array of Java strings; "..._rsja": Java array of
        //  Ruby strings; "..._rsra": Ruby array of Ruby strings.
        String[] animal_name_jsja = {
            "Lions", "Tigers", "Bears", "Elephants", "Gorillas", "Orangutans"
        } ;
        int animal_name_a_length = animal_name_jsja.length ;
        Ruby runtime = JavaEmbedUtils.initialize(Collections.emptyList()) ;
        RubyString[] animal_name_rsja = new RubyString[animal_name_a_length] ;
        for (int index = 0 ; index < animal_name_a_length ; index++) 
            animal_name_rsja[index] =
                RubyString.newString(runtime,
                                     animal_name_jsja[index].getBytes()) ;
        RubyArray animal_name_rsra =
            RubyArray.newArray(runtime,animal_name_rsja) ;
        animal_name_rsra.add(2014) ; return animal_name_rsra ;
    }
}
