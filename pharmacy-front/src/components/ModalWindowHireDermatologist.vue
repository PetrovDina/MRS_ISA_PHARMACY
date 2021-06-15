<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <table class="table table-hover">
                <thead>
                    <tr>
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
                        <td>{{der.username}}</td>
                        <td>{{der.email}}</td>
                        <td>{{der.firstName}}</td>
                        <td>{{der.lastName}}</td>
                        <td><i v-bind:class="der.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td> 
                        <td>{{der.rating}}</td>
                    </tr>
                </tbody>
            </table>
            <div class="md-form mx-5 my-5">
                <label class="label" for="start_time">Choose starting work time</label>
                <input type="time" id="start_time" class="form-control">
            </div>
            <div class="md-form mx-5 my-5">
                <label class="label" for="end_time">Choose ending work time</label>
                <input type="time" id="start_time" class="form-control">
            </div>
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
import moment from 'moment'

export default {
    name : "ModalWindowHireDermatologist",
    components : {Button},
    props : {
        modal_show : Boolean,
        pharmacyId : null,
        dermatologists: {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            selected_dermos : {id : -1},
        }
    },
    mounted() {
        if(!this.pharmacyId)
            this.closeWindow();
    },
    methods : {
        closeWindow : function(){
            this.selected_dermos = { id : -1}
            this.$emit('modal-closed');
        },
        clickedOnRow: function(der){
            this.selected_dermos = der;
        },
        checkInputFields : function(){
            if(this.selected_dermos.id == -1){
                this.$toasted.show("Please select one dermatologist!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return true;
            } 

            if(document.getElementById('start_time') === undefined || document.getElementById('end_time') === undefined){
                return true;
            }
            else {
                if(document.getElementById('start_time').value == ''){
                    this.$toasted.show("Please insert starting time!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
                if(document.getElementById('end_time').value == ''){
                    this.$toasted.show("Please insert ending time!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return true;
                }
            }
            return false;
        },
        hireDermatologist: function(){
            if(this.checkInputFields()) return;

            let time1 = moment().format(document.getElementById('start_time').value, 'HH:mm');
            let time2 = moment().format(document.getElementById('end_time').value, 'HH:mm');

            if(time1 >= time2){
                this.$toasted.show("Starting time must be before ending time!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            }
            else
            {
                let myWorkTime = {
                    startDate: moment().format("YYYY-MM-DD"),
                    startTime: time1,
                    endDate: moment().format("YYYY-MM-DD"),
                    endTime: time2
                };
                this.selected_dermos['workTime'] = myWorkTime;
                this.$emit('hired-dermatologist', this.selected_dermos);
                this.selected_dermos = { id : -1};
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