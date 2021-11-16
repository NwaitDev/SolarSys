#  ============================================================================
#
#            Last lab class with JRuby --- Model solution
#
#  ============================================================================
#
#  Author: J.-M. Hufflen
#  Date: December 2018
#
#  ============================================================================

include Java

#  ============================================================================
#  First steps

java_import 'java.lang.String' do |ignored_package,ignored_name| 'JString' end

p JString.methods

$s = JString.new('The quick brown jumps over the lazy dog.')
p $s.indexOf('brown')
p $s.indexOf('red')

class JString
  #
  def occurrence_nb(s0)
    start_index = 0
    result = 0
    while (next_index = indexOf(s0,start_index)) != -1
      result += 1
      start_index = next_index + 1
    end
    result
  end
  #
end

p $s.occurrence_nb(JString.new('The'))
p $s.occurrence_nb(JString.new('he'))

#  ============================================================================
#  Inheritance by implementing an abstract method

java_import 'jrubytrying.AbstractPerson' do |ignored_package,ignored_name|
  'AbstractPerson'
end

class ActualPerson < AbstractPerson
  #
  def hello
    puts 'Hi, everybody!'
  end
  #
end

$p0 = ActualPerson.new
p $p0.repeat 10

#  ============================================================================
#  Definitions to be read in Java

class Dog < Java::jrubytrying.Animal
  #
  def bark
    puts 'I\'m barking!'
  end
  #
end

Java::jrubytrying.Animal.__persistent__ = true

$kingkong = Java::jrubytrying.Animal.new('King Kong')

class << $kingkong
  #
  def skullisland
    puts 'I\'m at home!'
  end
  #
end

p $kingkong.skullisland
