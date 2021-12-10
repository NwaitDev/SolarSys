# interface graphique en Jruby

include Java

# $a = Java::jrubytrying.Imported_By_JRuby.make

$a = Java::javaclass.Animal.new("chien")

p $a.get_name()

p $a