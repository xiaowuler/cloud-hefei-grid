var Map = function(){

    this.features = [];
    this.polygons = [];

    this.Startup = function(){
        this.createEasyMap();
        this.setProBorder();
    };

    this.createEasyMap = function () {
        this.map = L.map("map", {
            center: [32.00666, 118.85758],
            zoom: 9.2,
            zoomControl: false
        });
    }

    this.clearLayers = function(){
        $(this.features).each(function (index, feature) {
            this.map.removeLayer(feature);
        }.bind(this));
        $(this.polygons).each(function (index, polygon) {
            this.map.removeLayer(polygon);
        }.bind(this))
    }

    this.drawPolygon = function(data) {
        $(data.box).each(function (index, item) {
            var latlngs = [[item.startLat, item.startLon], [item.startLat, item.endLon], [item.endLat, item.endLon], [item.endLat, item.startLon]];
            var polygon = L.polygon(
                latlngs,
                {
                    weight: 0.5,
                    color: 'yellow',
                    fillColor: this.getColorByValue(item.total, data.legendLevels),
                    fillOpacity: 0.9
                }
            ).addTo(this.map);
            this.polygons.push(polygon);
            this.createLabelsLayer(polygon, item.total.toFixed(2), null)
        }.bind(this));
    };

    this.createLabelsLayer = function (polygon, label) {
        var feature = new L.FeatureGroup();

        feature.addLayer(L.marker(polygon.getBounds().getCenter(), {
            icon: L.divIcon({
                className: 'box-label',
                html: label
            })
        }));

        this.map.addLayer(feature);
        this.features.push(feature);
    };

    this.getColorByValue = function (value, legendLevels) {
        var color = null;
        $(legendLevels).each(function (index, item) {
            if (item.beginValue <= value && item.endValue > value){
                color = 'rgb(' + item.color + ')';
                return;
            }
        }.bind(this));

        if (color == null){
            return 'green';
        }
        return color;
    }

    this.setProBorder = function () {
        $.getJSON("json/nanjing-districts.json", function (data) {
            this.borders = L.geoJson(data, {
                style: {
                    weight: 1,
                    opacity: 1.0,
                    color: '#fbae2e',
                    fillColor: '#fbdb39',
                    fillOpacity: 0.1
                }
            })
            this.map.addLayer(this.borders);
        }.bind(this));
    }

    this.setRegionName = function () {
        $.getJSON("json/nanjingprovice.json", function (labels) {
            this.features = new L.FeatureGroup();
            $(labels).each(function (index, label) {
                this.features.addLayer(L.marker([label.Latitude, label.Longitude], {
                    icon: L.divIcon({
                        className: 'district-name-label',
                        html: label.Name
                    })
                }));
            }.bind(this));
            this.map.addLayer(this.features);
        }.bind(this));
    }
};