$LOAD_PATH.append("..")
require 'uri'
require 'net/http'
require 'json'
require "./cinnamon/Calculs.rb"
include Java

class InvalidData < Exception
end

class CelestialBody
    attr_reader :name, :referenceFrame, :diameter, :periodOfRotation, :periodOfRevolution, :distanceFromOrigin, :position, :satelliteList, :semiMajorAxis, :eccentricity, :inclination, :ascendingNodeAngle, :periapsisArg, :mainAnom
    def initialize(name, referenceFrame, diameter, distanceFromOrigin,periodOfRevolution, periodOfRotation,position)  
        @name = name
        @referenceFrame = referenceFrame
        @diameter = diameter
        @periodOfRevolution = periodOfRevolution
        @periodOfRotation = periodOfRotation
        @distanceFromOrigin = distanceFromOrigin
        @position = position 
        @satelliteList = []
        @semiMajorAxis = nil
        @eccentricity = nil
        @inclination = nil
        @ascendingNodeAngle = nil
        @periapsisArg = nil
        @mainAnom = nil
    end

    #def setCartesianCoordinates(x,y,z,xSpeed,ySpeed,zSpeed)
    #    @position = CartesianCoord.new(x,y,z,xSpeed,ySpeed,zSpeed)
    #end

    def setKeplerianCoordinates(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
        @position = KeplerCoord.new(semiMajorAxis, eccentricity, inclination, ascendingNodeAngle, periapsisArg, mainAnom)
        @semiMajorAxis = semiMajorAxis
        @eccentricity = eccentricity
        @inclination = inclination
        @ascendingNodeAngle = ascendingNodeAngle
        @periapsisArg = periapsisArg
        @mainAnom = mainAnom
    end

    def getCoordAfter(dayShift)
        KeplerCoord.new(@semiMajorAxis, @eccentricity, @inclination, @ascendingNodeAngle, @periapsisArg, (@mainAnom+dayShift/@periodOfRevolution)%360).toCartesian(@referenceFrame)
    end

    def addSatellite(celestialBody)
        @satelliteList.push(celestialBody)             
    end

    def to_java()
        if (@position.type == :cartesian)
            x = @position.x
            z = @position.z
            y = @position.y
            jpoint = Java::vanilla.model.SpacePoint.new(x.to_java(:long),y.to_java(:long))
        elsif (@position.type == :keplerian)
            currentPos = @position.toCartesian(@referenceFrame)
            x = currentPos.x
            z = currentPos.z
            y = currentPos.y
            jpoint = Java::vanilla.model.SpacePoint.new(x.to_java(:long),y.to_java(:long))
        else
            jpoint = nil.to_java
        end

        scale = 1;

        Java::vanilla.model.CelestialBody.new(
            @name.to_java(:String),
            @referenceFrame.to_java,
            jpoint,
            @diameter.to_java(:float),
            scale,
            @periodOfRevolution.to_java(:float),
            @periodOfRotation.to_java(:float),
            @distanceFromOrigin.to_java(:float)
        )
    end
end
