################################################
########### Using Nasa API for fun #############
################################################
require 'uri'
require 'net/http'

uri = URI('https://api.nasa.gov/planetary/apod')
params = { :api_key => 'ChPtAQBf3t1E9yJBdunlr1ePOpfDQSoItfBswfmT' }
uri.query = URI.encode_www_form(params)

res = Net::HTTP.get_response(uri)
puts res.body if res.is_a?(Net::HTTPSuccess)

################################################
################################################

class CelestialBody
    attr_reader :name, :referenceFrame, :position, :diameter, :scale, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :clockwise

    def initialize(name, referenceFrame, position, diameter, scale, distanceFromOrigin,periodOfRevolution, periodOfRotation, clockwise=false)  
        @name = name
        @referenceFrame = referenceFrame
        @position = position
        @diameter = diameter
        @scale = scale
        @periodOfRevolution = periodOfRevolution
        @periodOfRotation = periodOfRotation
        @distanceFromOrigin = distanceFromOrigin
        @clockwise = clockwise
    end
end

class Sun < CelestialBody

    def initialize()
        super("Sun",self, [0,0], 100, 1, 0, 1, 1, true)
    end
end

$SolarSystemPlanets=[]
$sun = Sun.new()
$SolarSystemPlanets.push(CelestialBody.new("TestPlanet", $sun, [300,0], 10, 5, 1000, 365.25, 24, true))