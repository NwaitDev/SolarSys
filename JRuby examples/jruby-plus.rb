include Java

java_import 'java.lang.String' do |ignored_package,ignored_name| 'JString' end

$a = Java::jrubytrying.Some_String_Table.make()
p($a)
$htable = Hash[$a]
p($htable)

def check_classes(htable)
  result = true
  htable.each_pair do |presumed_java_s,presumed_ruby_s|
    presumed_java_s_class = presumed_java_s.class
    presumed_ruby_s_class = presumed_ruby_s.class
    p presumed_java_s_class
    p presumed_ruby_s_class
    result &&=
      ((presumed_java_s_class == String) and (presumed_ruby_s_class == String))
  end
  result
end

def test_strip_methods(htable)
  result = 0
  htable.each_pair do |java_s,ruby_s|
    js0 = JString::new(java_s).trim()
    rs0 = ruby_s.strip
    p js0
    p rs0
    result += 1 if js0.to_s == rs0
  end
  result
end

def matching_strings(htable,regexp)
  result = []
  htable.each_value do |value| result.push(value) if value =~ regexp end
  result
end

p(check_classes($htable))
p(test_strip_methods($htable))
p(matching_strings($htable,/S(t(a|r)|n)/))
