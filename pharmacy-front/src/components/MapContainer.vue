<template>
  <div ref="map-root"
       style="width: 100%; height: 500px;">
  </div>
</template>

<script>
  import View from 'ol/View'
  import Map from 'ol/Map'
  import TileLayer from 'ol/layer/Tile'
  import OSM from 'ol/source/OSM'
  import Feature from 'ol/Feature'
  import Point from 'ol/geom/Point'
  import * as proj from 'ol/proj'
  import Style from 'ol/style/Style'
  import Icon from 'ol/style/Icon'
  import * as layer from 'ol/layer'
  import Vector from 'ol/source/Vector'
  
  import 'ol/ol.css'

  export default {
    name: 'MapContainer',
    components: {},
    props: {
        coordinates: Array,
    },

    data() {
        return {
            lon: null,
            lat: null,
            map: null,
            markerFeature: null,
        }
    },

    mounted() {
        setTimeout(this.initMap, 150);
    },

    methods: {
        initMap() {
            this.markerFeature = new Feature({
                geometry: new Point(proj.fromLonLat([this.coordinates[0], this.coordinates[1]])),
            });

            this.markerFeature.setStyle(new Style({
                image: new Icon({
                    scale: 1,
                    src: 'https://nominatim.openstreetmap.org/ui/build/images/marker-icon.png'
                })
            }));

            var vectorLayer = new layer.Vector({
                source: new Vector({
                    features: [this.markerFeature],
					wrapX: true,
                }),
				wrapX: false,
            });

            const center = proj.transform(this.coordinates, 'EPSG:4326', 'EPSG:3857');

            new Map({
                target: this.$refs['map-root'],
                layers: [
                    new TileLayer({
                        source: new OSM({wrapX:false})
                    }),
                    vectorLayer
                ],
                view: new View({
                    zoom: 17,
                    center: center,
                }),
            });
        
    },

    
    },
  }
</script>