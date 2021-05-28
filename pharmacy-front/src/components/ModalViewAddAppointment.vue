<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <h2>Appointment creation</h2>
            <div v-if="hasAnyAvailableAppointment()">
                <span> Dermatologist worktime : {{dermatologistToSend.workTime.startTime}} - {{dermatologistToSend.workTime.endTime}}</span><br>
                <br><span>All available appointments</span>
                <table class="table table-hover" >
                    <thead>
                        <tr>
                            <th scope="col"> </th>
                            <th scope="col">Dermatologist</th>
                            <th scope="col">Start</th>
                            <th scope="col">End</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr :key="appointment.id" v-for="appointment in appointments">
                            <td><button @click="deleteAppointment(appointment.id)"><i class="fa fa-trash fa-lg"></i></button></td>
                            <td>{{appointment.employee.firstName}} {{appointment.employee.lastName}}</td>
                            <td><i class="fa fa-calendar" aria-hidden="true"></i> {{convertDate(appointment.timePeriod.startDate)}} - <i class="fa fa-clock-o"></i> {{convertTime(appointment.timePeriod.startTime)}}</td> 
                            <td><i class="fa fa-calendar" aria-hidden="true"></i> {{convertDate(appointment.timePeriod.endDate)}} - <i class="fa fa-clock-o"></i> {{convertTime(appointment.timePeriod.endTime)}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="md-form mx-5 my-5">
                <label for="date">Pick up date</label>
                <input type="date" id="date" name="date" :min="today"
                    class="form-control"/>
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
        dermatologistToSend: Object,
        appointments : {
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
        hasAnyAvailableAppointment: function(){
            if(this.appointments === undefined) return false;
            return this.appointments.length != 0;
        },
        checkNewAppointment : function(date, start_time, end_time){
            date = this.convertDate(date);
            start_time = this.convertTime(start_time);
            end_time = this.convertTime(end_time);
            // provjera za radno vrijeme
            if(!(this.convertTime(this.dermatologistToSend.workTime.startTime) <= start_time) 
                || !(this.convertTime(this.dermatologistToSend.workTime.endTime) >= end_time)) {
                this.$toasted.show("Dermatologist does not work at that time!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return false;    
            }

            let startDateTime = moment(date+"T"+start_time).format();
            let endDateTime = moment(date+"T"+end_time).format();

            for (const appointmentIndex in this.dermatologistToSend.appointments) {
                if (Object.hasOwnProperty.call(this.dermatologistToSend.appointments, appointmentIndex)) {
                    const appointment = this.dermatologistToSend.appointments[appointmentIndex];
                    let appoStartDateTime = moment(appointment.timePeriod.startDate+"T"+appointment.timePeriod.startTime).format();
                    let appoEndDateTime = moment(appointment.timePeriod.endDate+"T"+appointment.timePeriod.endTime).format();
                     if (!(appoStartDateTime > endDateTime
                            && appoStartDateTime > startDateTime)
                                && !(appoEndDateTime < endDateTime
                                    && appoEndDateTime < startDateTime)){
                                        this.$toasted.show("An appointment at that time already exists!", {
                                            theme: "toasted-primary",
                                            position: "top-center",
                                            duration: 2000,
                                        });
                                        return false;
                                    }
                }
            }
            return true;
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
                if(this.checkNewAppointment(date, start_time_value, end_time_value)){
                    // poziv na back appointmente kontroleru
                    client({
                    url: "appointments",
                    method: "POST",
                    data: {
                            employee: {
                                id: this.dermatologistToSend.id
                            },
                            timePeriod: {
                                startDate: date,
                                startTime:  start_time_value,
                                endDate: date,
                                endTime:  end_time_value
                            },
                            pharmacy: {
                                id: this.dermatologistToSend.pharmacyId
                            }
                        }
                    })
                    .then((response) => {
                            this.$toasted.show("An appointment has been successfully added!", {
                                theme: "toasted-primary",
                                position: "top-center",
                                duration: 2000,
                            })
                            this.closeWindow();    
                        }
                    )
                }
            }
        },
        deleteAppointment : function(appointmentId){
            client({
                url: "appointments/" + appointmentId,
                method: "DELETE"
            })
            .then(() => {
                this.$emit('appointment-deleted', appointmentId);
                this.$toasted.show("Appointment successfuly deleted!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
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

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

tr.selected {
	 background-color: rgba(155, 82, 151, 0.527);
}
</style>