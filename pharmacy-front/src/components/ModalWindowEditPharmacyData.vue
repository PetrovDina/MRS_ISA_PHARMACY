<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div>
                <h2>Edit pharmacy data </h2>
            </div>
            <hr class="solid">
            <div class="container">
                <div class="row">
                    <div class="col-4">
                        <span>Name</span>
                        <hr class="solid">
                        <label class="label" for="name">Pharmacy name:</label>
                        <input type="text" id="name" class="form-control" :value="pharmacy.name">
                    </div>
                    <div class="col-4">
                        <span>Address</span>
                        <hr class="solid">
                        <label class="label" for="city">City:</label>
                        <input type="text" id="city" class="form-control" :value="pharmacy.location.city">
                        <label class="label" for="street">Street:</label>
                        <input type="text" id="street" class="form-control" :value="pharmacy.location.street">
                        <label class="label" for="streetNum">Street number:</label>
                        <input type="text" id="streetNum" class="form-control" :value="pharmacy.location.streetNum">
                    </div>
                    <div class="col-4">
                        <span>Price cataloge</span>
                        <hr class="solid">
                        <label class="label" for="examinationPrice">Examination price:</label>
                        <input type="text" id="examinationPrice" class="form-control" :value="pharmacy.appointmentPriceCatalog.examinationPrice">
                        <label class="label" for="consultationPrice">Consultation price:</label>
                        <input type="text" id="consultationPrice" class="form-control" :value="pharmacy.appointmentPriceCatalog.consultationPrice">
                    </div>
                </div>
            </div>
            <Button 
                @action-performed="saveChangedData()" 
                text="Save" 
                bgd_color="rgba(15, 95, 72, 0.95)" 
                style="color:white; width:200px; text-align:center; margin: auto">
            </Button>
        </div>
    </div>
</template>

<script>
import Button from "./Button";

export default {
    name : "ModalWindowEditPharmacyData",
    components : {Button},
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
        },
        checkFiledsUndefined : function(){
            if(document.getElementById('name') == undefined) return true; 
            if(document.getElementById('city') == undefined) return true; 
            if(document.getElementById('street') == undefined ) return true; 
            if(document.getElementById('streetNum') == undefined) return true; 
            if(document.getElementById('examinationPrice') == undefined ) return true; 
            if(document.getElementById('consultationPrice') == undefined) return true;
            return false;
        },
        areFiledsUnchanged : function(){
            if(document.getElementById('name').value != this.pharmacy.name) return false;
            if(document.getElementById('city').value != this.pharmacy.location.city) return false; 
            if(document.getElementById('street').value != this.pharmacy.location.street ) return false; 
            if(document.getElementById('streetNum').value != this.pharmacy.location.streetNum) return false; 
            if(parseInt(document.getElementById('examinationPrice').value) != this.pharmacy.appointmentPriceCatalog.examinationPrice) return false; 
            if(parseInt(document.getElementById('consultationPrice').value) != this.pharmacy.appointmentPriceCatalog.consultationPrice) return false;
            return true;
        },
        checkEmptyFiled : function(field, msg){
            if(document.getElementById(field).value == ''){
                this.$toasted.show(msg, {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return true;
            }
            return false;
        },
        saveChangedData : function(){
            if(this.areFiledsUnchanged()){
                this.$toasted.show("You did not change any field!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }
            else
                if(!this.checkFiledsUndefined()){
                    if(this.checkEmptyFiled('name', "Please insert pharmacy name!")) return;
                    if(this.checkEmptyFiled('city', "Please insert city!")) return; 
                    if(this.checkEmptyFiled('street', "Please insert street!")) return ; 
                    if(this.checkEmptyFiled('streetNum', "Please insert street number!")) return; 
                    if(this.checkEmptyFiled('examinationPrice', "Please insert examination price!")) return; 
                    if(this.checkEmptyFiled('consultationPrice', "Please insert consultation price!")) return;
                    // saljemo podatke
                    this.$emit('pharamcy-data-changed', {
                        id : this.pharmacy.id,
                        name: document.getElementById('name').value,
                        location : {
                            city: document.getElementById('city').value,
                            street: document.getElementById('street').value,
                            streetNum: document.getElementById('streetNum').value,
                        },
                        appointmentPriceCatalog: {
                            examinationPrice : document.getElementById('examinationPrice').value,
                            consultationPrice : document.getElementById('consultationPrice').value
                        }
                    })
                    this.closeWindow();
            }
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