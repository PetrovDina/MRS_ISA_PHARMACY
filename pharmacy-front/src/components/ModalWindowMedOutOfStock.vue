<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div v-if="hasAnyMedicationOutOfStock()">
                <span>All medication that are not on stock</span>
                <table class="table table-hover" >
                    <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Prescription</th>
                            <th scope="col">Form</th>
                            <th scope="col">Quantity</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr :key="med.id" v-for="med in medications">
                            <td>{{med.name }}</td>
                            <td>{{med.manufacturer}}</td> 
                            <td>{{med.prescriptionReq? "required":"not required"}}</td> 
                            <td>{{med.form}}</td>
                            <td>{{med.quantity}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div v-if="!hasAnyMedicationOutOfStock()">
                <br><h2>There are no medication out of stock !</h2>
            </div>
        </div>
    </div>
</template>

<script>
import Button from './Button.vue';

export default {
    name : "ModalWindowMedOutOfStock",
    components : {Button},
    props : {
        modal_show : Boolean,
        medications : {
            type : Array,
            default() {
                return [];
            }
        },
    },
    methods :{
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        hasAnyMedicationOutOfStock : function(){
            if(this.medications === undefined) return false;
            return this.medications.length != 0;  
        },
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
  z-index: 1; /* Siit on top */
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
  width: 60%;
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

tr.selected {
	 background-color: rgba(155, 82, 151, 0.527);
}
</style>