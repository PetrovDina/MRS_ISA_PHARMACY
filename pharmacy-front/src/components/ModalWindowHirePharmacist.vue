<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <RegistrationComponent 
                typeToRegister="PHARMACIST" 
                :pharmacyId = "pharmacyId"
                @registered = "registeredPharmacist"
            >
            </RegistrationComponent>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import RegistrationComponent from './RegistrationComponent.vue';

export default {
    name : "ModalWindowHirePharmacist",
    components : {Button, RegistrationComponent},
    props : {
        modal_show : Boolean,
        pharmacyId : {
            type : Number,
            default() {
                return -1;
            }
        },
    },
    data() {
        return {}
    },
    mounted() {},
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        registeredPharmacist: function(pharmacist){
           this.$emit('registeredPharmacist', pharmacist);
        }
    }
};
</script>


<style scoped>

#close_btn{
    height: 30px;
    width: 25px;
}

.v-text-field{
    margin: 10px 60px;
    width: 300px;
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

#dermos {
    margin: 30px 10px 10px 10px;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

tr.selected {
	 background-color: rgba(155, 82, 151, 0.527);
}
</style>