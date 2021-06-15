<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div>
                <h1>{{pharmacy.name}}</h1>
                <h2>{{pharmacy.location.street}} {{pharmacy.location.streetNum}}, {{pharmacy.location.city}}</h2>
            </div>
            <hr class="solid">
            <MapContainer
                :coordinates = "[pharmacy.location.longitude, pharmacy.location.latitude]"
                >
            </MapContainer>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import MapContainer from "./MapContainer.vue"

export default {
    name : "ModalWindowEditPharmacyData",
    components : {Button, MapContainer},
    props : {
        modal_show : Boolean,
        pharmacy : Object
    },
    data() {
        return {}
    },
    mounted() {},
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        }
    }
};
</script>


<style scoped>

#close_btn{
    height: 30px;
    width: 25px;
}

body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
  display: block; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

</style>