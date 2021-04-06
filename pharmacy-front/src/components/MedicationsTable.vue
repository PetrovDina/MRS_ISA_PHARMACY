<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th scope="col">Reservation</th>
                    <td><Button @action-performed="addButton()" class="btn-success" text="Add" color="grey"></Button></td>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications">
                    <td>{{med.id}}</td>
                    <td>{{med.name }}</td>
                    <td>{{med.manufacturer}}</td> 
                    <td>{{med.prescriptionReq? "required":"not required"}}</td> 
                    <td>{{med.form}}</td>
                    <td>{{med.quantity}}</td>
                    <td>{{med.price}}</td>
                    <td><Button class="btn-success" text="Reserve" color="green"></Button></td>
                </tr>
            </tbody>
        </table>
        <!-- The Modal -->
        <div v-if="prikazan === true" id="myModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content">
                <span class="close"><Button id="close_btn" @action-performed="closeModal()" class="btn" text="X" color="red"></Button></span>
                <p>Some text in the Modal..</p>
            </div>
        </div>
    </div>
</template>

<script>

import Button from './Button.vue';

export default {
    name: "MedicationsTable",
    components: {Button},
    props: {
        medications: {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            prikazan : false
        };
    },
    methods: {
        addButton : function(){
            this.prikazan = !this.prikazan;
        },
        closeModal : function(){
            this.prikazan = !this.prikazan;
        }
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#medicationsViewDiv{
    margin: 30px 10px 10px 10px;;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

#close_btn{
    float:right;
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