# An utilitary file to compute the distances between celestial bodies and convert them into different units

class Distance

    def self.km_to_miles(km)
        km * 0.621371
    end

    def self.miles_to_mk(miles)
        miles * 1609 
    end

    # distance ratio between the distance and the Earth -> Sun distance.
    def self.km_to_AU(km)
        km / 149600000
    end

    def self.AU_to_km(d)
        d * 149600000
    end
        
end