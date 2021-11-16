#  ============================================================================
#  Examples using JRuby --- Seminex, 15th May 2014
#  Do not use "irb" or "ruby", but "jirb" or "jruby".

include Java

java_import ['java.lang.String','java.util.Vector'] do |package,name|
  "J#{name}"
end

p java.lang.Math::PI
p JString.new('The quick brown fox jumps over the lazy dog.').indexOf('brown')

$a = (2.upto(11).inject([]) do |a0,i| a0.push(i) end).to_java(:int)

p $a
p $a[0]

class MyThread < java.lang.Thread
  def run
    puts 'Hi, everybody!'
  end
end

MyThread.new.start

$v = JVector.new
$v.add_element('Lions')
$v.add_element('Tigers')
$v.add_element('Bears')
$v.add_element('Elephants')
$v.add_element('Gorillas')
$v.add_element('Orangutans')

java.util.Collections::sort($v,java.util.Comparator.impl do |method,*args|
  case method.to_s
    when 'compare'
      args[0] <=> args[1]
    when 'equals'
      args[0] == args[1]
  end
end)

print '<< ' 
$v.each do |s|
  print s, ' '
end
puts '>>'
