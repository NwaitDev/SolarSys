//  ===========================================================================
//  Rev. Nov. 2019 w.r.t. new constructor emptyList()

package jrubytrying ;

import org.jruby.javasupport.JavaEmbedUtils ; import org.jruby.Ruby ;
import org.jruby.RubyArray ; import org.jruby.RubyString ;
import java.util.Collections ;

public class Some_String_Table {
    public static RubyArray make() {
        //  "..._jsja": Java array of Java strings; "..._rsja": Java array of
        //  Ruby strings; "..._rsra": Ruby array of Ruby strings, "..._ra":
        //  heterogenous Ruby array.
        String[] test_jsja = {
            " Star Wars", "The Empire Strikes Back", "Return of the Jedi ",
            " Rangoon", "Snake  Eyes", " Welcome  to the Punch ",
            " Go Fast!    " 
        } ;
        int test_length = test_jsja.length ;
        Ruby runtime = JavaEmbedUtils.initialize(Collections.emptyList()) ;
        RubyArray test_ra = RubyArray.newArray(runtime) ;
        for (int index = 0 ; index < test_length ; index++) {
            String js = test_jsja[index] ;
            RubyArray test_sub_ra = RubyArray.newArray(runtime) ;
            test_sub_ra.add(js) ;
            test_sub_ra.add(RubyString.newString(runtime,js.getBytes())) ;
            test_ra.add(test_sub_ra) ;
        }
        return test_ra ;
    }
}

