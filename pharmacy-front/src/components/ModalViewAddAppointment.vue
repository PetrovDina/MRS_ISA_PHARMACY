<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <span>Appointment creation</span>
            <div class="md-form mx-5 my-5">
                <label for="date">Pick up date</label>
                <input type="date" id="date" name="date" :min="today"
                    class="form-control" v-model="chosenDate"/>
            </div>
            <div>
                <div class="md-form mx-5 my-5">
                    <label class="label" for="start_time">Choose starting time</label>
                    <input type="time" id="start_time" class="form-control">
                </div>
                <div class="md-form mx-5 my-5">
                    <label class="label" for="end_time">Choose ending time</label>
                    <input type="time" id="end_time" class="form-control">
                </div>
            </div>
            <Button 
                    @action-performed="addAppointment()" 
                    text="Add appointment" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center;margin: auto">
            </Button>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import moment from 'moment'
import { client } from "@/client/axiosClient";

export default {
    name : "ModalWindowAddAppointment",
    components : {Button },
    props : {
        modal_show : Boolean,
        dermatologistIdToSend: Number,
    },
    data() {
        return {
            chosenDate: null,
            today: moment().format("YYYY-MM-DD")
        }
    },
    mounted() {
        if (!this.dermatologistIdToSend){
            this.closeWindow();
        }   
    },
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        addAppointment: function(){
            // treba da provjere vremena i da se turi u bazu
            let date = document.getElementById('date').value;
            let start_time_value = document.getElementById('start_time').value;
            let end_time_value = document.getElementById('end_time').value;

            if(!date || !start_time_value || !end_time_value){
                alert("All inputs must be filled!");
            }
            else
            {
                // poziv na back appointmente kontroleru
                client({
                url: "appointments",
                method: "POST",
                data: {
                        employeeDTO: {
                            //id: this.employeeIdToSend //// JER ZA SAD POSTOJI SAMO JEDAN DERMOS
                            id: this.dermatologistIdToSend
                        },
                        timePeriodDTO: {
                            startDate: date,
                            startTime: start_time_value,
                            endDate: date,
                            endTime: end_time_value
                        }
                    }
                })
                .then((response) => alert("Succsessfull!"))
                .catch((response) => alert("Sorry... An appointment at that time is not available!"));
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
  width: 45%;
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