<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <AddNewMedicationTable 
                @clicked-on-row="selectMedication" 
                :medications="medications">
            </AddNewMedicationTable>
            <div style="display: block">
                <form onsubmit="return false;"> 
                    <div style="margin-left: 70px;" align="left">
                        <label for="quantity">Quantity: </label>
                        <input style="border:solid 2px rgba(15, 95, 72, 0.95);" type="number" id="quantity" v-model="quantity"
                        required="" oninvalid="this.setCustomValidity('Please enter quantity.')"  oninput="setCustomValidity('')">
                    </div>
                    <Button 
                        @action-performed="addMedication(selectedMedication, quantity)" 
                        text="Add medication" 
                        bgd_color="rgba(15, 95, 72, 0.95)" 
                        style="color:white">
                    </Button>
                </form>
                
            </div>
            
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";
import AddNewMedicationTable from "./AddNewMedicationTable";

export default {
    name : "ModalWindow",
    components : {Button, AddNewMedicationTable},
    props : {
        modal_show : Boolean,
    },
    data() {
        return {
            medications : [],
            selectedMedication : null,
            quantity : '',
        }
    },
    mounted() {
        client({
                url: "med/all",
                method: "GET",
            }).then((response) => (this.medications = response.data));
    },
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        selectMedication : function(med) {
    		this.selectedMedication = med;
    	},
        addMedication : function(med, quantity){
            if(!med) 
            {
                this.$toasted.show("Please select medication.", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                return;
            }
            if(this.quantity=='') return;
            if(this.quantity < 1) 
            { 
                this.$toasted.show("Quantity must be greater than 0.", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                return; 
            }

            this.$emit('add-medication', med, quantity);
            this.selectedMedication = null;
            this.quantity = '';
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
</style>