<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <AddNewMedicationTable 
                @clicked-on-row="selectMedication" 
                :medications="medications">
            </AddNewMedicationTable>
            <div style="display: block">
                <v-text-field
                    label="Medication price"
                    :rules="rules"
                    hide-details="auto"
                    v-model="price"
                ></v-text-field>
                <Button 
                    @action-performed="addMedication(selectedMedication, price)" 
                    text="Add medication" 
                    bgd_color="rgba(32, 102, 75, 0.95)" 
                    style="color:white">
                </Button>
            </div>
            <label v-if="error_msg">{{error_msg_text}}</label>
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
            rules : [
                value => !!value || 'You need to enter price.',
                //value => (value && value.length >= 3) || 'Min 3 characters',
            ],
            selectedMedication : null,
            price : 0,
            error_msg : false,
            error_msg_text : '',
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
            this.error_msg = false;
    	},
        addMedication : function(med, price){
            if(med === null || price == 0) {
                this.error_msg = true;
                this.error_msg_text = "Select medication and set a price!";
            }
            else {
                this.selectedMedication = null;
                this.$emit('add-medication', med, price);
                this.$emit('modal-closed');
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