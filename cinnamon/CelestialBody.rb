$LOAD_PATH.append("..")
require 'uri'
require 'net/http'
require 'json'
require "./cinnamon/Calculs.rb"
include Java


class CelestialBody
    attr_reader :name, :referenceFrame, :diameter, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :position, :satelliteList
    def initialize(name, referenceFrame, diameter, distanceFromOrigin,periodOfRevolution, periodOfRotation,position=Coords.new())  
        @name = name
        @referenceFrame = referenceFrame
        @diameter = diameter
        @periodOfRevolution = periodOfRevolution
        @periodOfRotation = periodOfRotation
        @distanceFromOrigin = distanceFromOrigin
        @position = position 
        @satelliteList = []
    end

    def setCartesianCoordinates(x,y,z,xSpeed,ySpeed,zSpeed)
        @position = CartesianCoord.new(x,y,z,xSpeed,ySpeed,zSpeed)
    end

    def setKeplerianCoordinates(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
        @position = KeplerCoord.new(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
    end

    def addSatellite(celestialBody)
        @satelliteList.push(celestialBody)             
    end

    def to_java()
        if :cartesian == @position.type
            x = @position.x
            y = @position.y
            jpoint = Java::java.awt.Point.new(x.to_java(:int),y.to_java(:int))
        elsif :keplerian == @position.type
            currentPos = @position.toCartesian(@referenceFrame)
            x = currentPos.x
            y = currentPos.y
            jpoint = Java::java.awt.Point.new(x.to_java(:int),y.to_java(:int))
        else
            jpoint = nil.to_java
        end

        Java::vanilla.model.CelestialBody.new(
            @name.to_java(:String),
            @referenceFrame.to_java,
            jpoint,
            @diameter.to_java(:float),
            @periodOfRevolution.to_java(:float),
            @periodOfRotation.to_java(:float),
            @distanceFromOrigin.to_java(:float)
        )
    end
end
