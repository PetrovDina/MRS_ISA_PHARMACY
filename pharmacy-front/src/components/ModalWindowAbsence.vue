<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div v-if="hasAnyAbsenceRequest()">
                <span>All requested absences</span>
                <table class="table table-hover" >
                    <thead>
                        <tr>
                            <th scope="col">Start</th>
                            <th scope="col">End</th>
                            <th scope="col">Accept?</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr :key="absence.id" v-for="absence in absences">
                            <td><i class="fa fa-calendar" aria-hidden="true"></i> {{convertDate(absence.timePeriod.startDate)}}</td> 
                            <td><i class="fa fa-calendar" aria-hidden="true"></i> {{convertDate(absence.timePeriod.endDate)}}</td>
                            <td>
                                <button style="margin-right: 2%" @click="acceptAbsence(absence)"><i class="fa fa-check fa-2x"></i></button>
                                <button style="margin-left: 2%" @click="rejectAbsence(absence)"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <textarea style="border : 2px solid black" id="absence-text-area" rows="3" cols="50" placeholder="Rejection reason...">
                </textarea>
            </div>
            <div v-if="!hasAnyAbsenceRequest()">
                <br><h2>There are no absence/vacation requests!</h2>
            </div>
        </div>
    </div>
</template>

<script>
import moment from 'moment'
import { client } from "@/client/axiosClient";
import Button from './Button.vue';

export default {
    name : "ModalWindowAbsence",
    components : {Button},
    props : {
        modal_show : Boolean,
        absences : {
            type : Array,
            default() {
                return [];
            }
        },
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
                url: "absences/accept",
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
                this.$emit('absence-remove-from-list', absence);
            })
        },
        rejectAbsence : function(absence){
            let textAreaFiled = document.getElementById("absence-text-area").value
            if(textAreaFiled === ''){
                this.$toasted.show("You need to fill rejection reason!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                return false;
            }
            console.log(absence);
            client({
                url: "absences/reject",
                method: "PUT",
                data : absence,
                params : {
                    rejectionReason : textAreaFiled
                }
            })
            .then((response) => {
                if(response.data)
                    this.$toasted.show("Absence rejected!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                this.$emit('absence-remove-from-list', absence);
            })
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