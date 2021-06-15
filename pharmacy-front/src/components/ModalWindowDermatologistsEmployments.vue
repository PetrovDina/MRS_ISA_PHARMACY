<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <!-- <div> -->
                <h3>{{dermatologist.firstName}} {{dermatologist.lastName}} works in: </h3><br>
                <table class="table table-hover" >
                    <thead>
                        <tr>
                            <th scope="col">Pharmacy name</th>
                            <th scope="col">Location</th>
                            <th scope="col">Worktime</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr :key="employment.id" v-for="employment in employments">
                            <td v-if="employment.pharmacy.id == pharmacyId"><b>{{employment.pharmacy.name}}</b></td>
                            <td v-if="employment.pharmacy.id != pharmacyId">{{employment.pharmacy.name}}</td>
                            <td>{{employment.pharmacy.location.street}} {{employment.pharmacy.location.streetNum}}, {{employment.pharmacy.location.city}}</td>
                            <td :class="employment.id == pharmacyId ? 'thisPharmacy' : ''">{{employment.workTime.startTime}} - {{employment.workTime.endTime}}</td>
                        </tr>
                    </tbody>
                </table>
            <!-- </div> -->
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import moment from 'moment'
import { client } from "@/client/axiosClient";

export default {
    name : "ModalWindowDermatologistsEmployments",
    components : {Button },
    props : {
        modal_show : Boolean,
        dermatologist : Object,
        employments : {
            type : Array,
            default() {
                return [];
            }
        },
        pharmacyId : Number
    },
    data() {
        return {
            today: moment().format("YYYY-MM-DD")
        }
    },
    mounted() {
        if (this.dermatologistToSend === {}){
            this.closeWindow();
        }   
    },
    methods : {
        convertDate : function(date){
            return moment(date).format("YYYY-MM-DD");
        },
        convertTime : function(time){
            return moment().format(time, "HH:mm");
        },
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        hasAnyAbsenceRequest : function(){
            if(this.absences === undefined) return false;
            return this.absences.length != 0;  
        },
        acceptAbsence : function(absence){
             client({
                url: "absence/accept",
                method: "PUT",
                data: absence
            })
            .then((response) => {
                if(response.data)
                    this.$toasted.show("Absence succsessfully accepted!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
            })
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

.thisPharmacy {
    background-color: rgba(155, 82, 151, 0.527);
}
</style>