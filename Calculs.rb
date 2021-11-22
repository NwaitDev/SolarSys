#EPOCH = 1 jan 2000
def absoluteDayFromEpoch(Y,M,D, UT=0)
    367*Y - 7 * ( Y + (M+9)/12 ) / 4 - 3 * ( ( Y + (M-9)/7 ) / 100 + 1 ) / 4 + 275*M/9 + D - 730515 + UT/24
end

class Position
    attr_reader :x, :y, :z
end

class Ellipse

    attr_reader :semiminorAxis, :semimajorAxis, :excentricity, :semifociDist

    def initialize(semiminorAxis, semimajorAxis)
        @semiminorAxis = semimajorAxis
        @semimajorAxis = semimajorAxis
        @excentricity = Math.sqrt(1-(semiminorAxis/semimajorAxis)*(semiminorAxis/semimajorAxis))
        @semifociDist = semimajorAxis*excentricity
    end

    def getPointAtAngle()
        
    end
end