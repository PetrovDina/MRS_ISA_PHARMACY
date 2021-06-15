<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div id="medFromOrder">
            <div class="modal-content">
                <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
                <label>{{medication.name}} by {{medication.manufacturer}} is not in pharmacy storage, please set the price if you want to order it.</label><br>
                <div class="md-form mx-5 my-5">
                    <div class="md-form mx-5 my-5">
                        <label for="medicationPrice" >Medication price:</label><br>
                        <input type="text" name="medicationPrice" id="medicationPrice" class="form-control">
                    </div>
                    
                    <Button 
                        @action-performed="addMedicationIntoPharmacyStorage()" 
                        text="Add medication" 
                        bgd_color="rgba(15, 95, 72, 0.95)" 
                        style="color:white; width:200px; text-align:center;">
                    </Button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Button from "./Button";

export default {
    name : "ModalWindowMakeNewOrder",
    components : {Button},
    props : {
        modal_show : Boolean,
        medication : Object,
    },
    data() {
        return {}
    },
    mounted() {},
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        addMedicationIntoPharmacyStorage : function(){
            if(document.getElementById("medicationPrice").value == '' || parseInt(document.getElementById("medicationPrice").value) <=0){
                this.$toasted.show("Please insert a proper value for medication price", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }
            let medicationToSend = this.medication;
            medicationToSend['price'] = parseInt(document.getElementById("medicationPrice").value);
            this.$emit("med-added", medicationToSend);
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

#dermos {
    margin: 30px 10px 10px 10px;
}

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

#medFromOrder {
    margin: 120px 60px 30px 60px;
}
</style>