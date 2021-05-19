<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div>
                <h2>Edit pharmacy data - {{pharmacy.name}}</h2>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-3">
                    </div>
                    <div class="col-6">
                        <label class="label" for="name">Pharmacy name:</label>
                        <input type="text" id="name" class="form-control" :value="pharmacy.name">
                        <label class="label" for="city">City</label>
                        <input type="text" id="city" class="form-control" :value="pharmacy.location.city">
                        <label class="label" for="street">Street</label>
                        <input type="text" id="street" class="form-control" :value="pharmacy.location.street">
                        <label class="label" for="streetNum">Street number</label>
                        <input type="text" id="streetNum" class="form-control" :value="pharmacy.location.streetNum">
                    </div>
                    <div class="col-3">
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
        saveChangedData : function(){
            if(document.getElementById('name') === undefined || document.getElementById('city') === undefined
             || document.getElementById('street') === undefined || document.getElementById('streetNum') === undefined){
                return true;
            }
            else {
                if(document.getElementById('name').value == ''){
                    this.$toasted.show("Please insert pharmacy name!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
                if(document.getElementById('city').value == ''){
                    this.$toasted.show("Please insert city!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
                if(document.getElementById('street').value == ''){
                    this.$toasted.show("Please insert street!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
                if(document.getElementById('streetNum').value == ''){
                    this.$toasted.show("Please insert street number!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
            }
            if(document.getElementById('name').value == this.pharmacy.name && document.getElementById('city').value == this.pharmacy.location.city
             && document.getElementById('street').value == this.pharmacy.location.street && document.getElementById('streetNum').value == this.pharmacy.location.streetNum){
                this.$toasted.show("You did not change any field!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
            }
            else {
                this.$emit('pharamcy-data-changed', {
                    name: document.getElementById('name').value,
                    location : {
                        city: document.getElementById('city').value,
                        street: document.getElementById('street').value,
                        streetNum: document.getElementById('streetNum').value,
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