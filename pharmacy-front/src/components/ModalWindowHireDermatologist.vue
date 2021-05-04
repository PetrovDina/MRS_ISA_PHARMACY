<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Rating</th>
                    </tr>
                </thead>
                <tbody>
                    <tr :key="der.id" v-for="der in dermatologists" @click="clickedOnRow(der)" v-bind:class="{selected : selected_dermos.id===der.id}">
                        <td>{{der.id}}</td>
                        <td>{{der.username}}</td>
                        <td>{{der.email}}</td>
                        <td>{{der.firstName}}</td>
                        <td>{{der.lastName}}</td>
                        <td><i v-bind:class="der.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td> 
                        <td>{{der.rating}}</td>
                    </tr>
                </tbody>
            </table>
            <div>
                <div class="md-form mx-5 my-5">
                    <label class="label" for="start_time">Choose starting work time</label>
                    <input type="time" id="start_time" class="form-control">
                </div>
                <div class="md-form mx-5 my-5">
                    <label class="label" for="end_time">Choose ending work time</label>
                    <input type="time" id="end_time" class="form-control">
                </div>

            </div>
            <label v-if="error_msg">{{error_msg_text}}</label>
            <Button 
                    @action-performed="hireDermatologist()" 
                    text="Hire dermatologist" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center; margin: auto">
            </Button>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import { client } from "@/client/axiosClient";
import moment from 'moment'

export default {
    name : "ModalWindowHireDermatologist",
    components : {Button},
    props : {
        modal_show : Boolean,
        pharmacyId : null,
    },
    data() {
        return {
            selected_dermos : {},
            dermatologists : [],
            error_msg_text: "",
            error_msg : true,
        }
    },
    mounted() {
        if(!this.pharmacyId)
            this.closeWindow();
        // ucitavamo sve dostupne dermatologe
        client({
        url: "dermatologists/all",
        method: "GET"
        })
        .then((response) => {
                this.dermatologists = response.data;
            }
        )
        .catch((response) => console.log("Dermatolozi nisu ucitani"));
    },
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        clickedOnRow: function(der){
            this.selected_dermos = der;
        },
        hireDermatologist: function(){
            
            let start_time_value = document.getElementById('start_time').value;
            let end_time_value = document.getElementById('end_time').value;

            let time1 = moment().format(start_time_value, 'HH:mm');
            let time2 = moment().format(end_time_value, 'HH:mm');

            if(time1 > time2){
                this.error_msg_text = "Starting time must be before ending time!";
                this.error_msg = true;
            }
            else
            {
                let myWorkTime = {
                    startDate: "2021-04-03",
                    startTime: time1,
                    endDate: "2021-04-03",
                    endTime: time2
                };
                this.selected_dermos['workTime'] = myWorkTime;
                this.$emit('hired-dermatologist', this.selected_dermos);
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