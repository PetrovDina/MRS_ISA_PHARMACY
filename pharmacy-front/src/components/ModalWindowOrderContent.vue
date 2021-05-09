<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <label> Expired date - <span style="font-size: 20px; color:rgba(32, 102, 75, 0.50);">{{convertDate(order.dueDate)}}</span></label>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Manufacturer</th>
                        <th scope="col">Form</th>
                        <th scope="col">Ordered quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <tr :key="orderItem.id" v-for="orderItem in order.orderItems">
                        <td style="width:23%;">{{orderItem.medication.name}}</td>
                        <td style="width:23%;">{{orderItem.medication.manufacturer}}</td>
                        <td style="width:23%;">{{orderItem.medication.form}}</td>
                        <td style="width:15%; background-color:rgba(155, 82, 151, 0.527);">{{orderItem.quantity}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import moment from "moment";

export default {
    name : "ModalWindowOrderContent",
    components : {Button},
    props : {
        modal_show : Boolean,
        order : Object,
    },
    data() {
        return {}
    },
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        convertDate : function(date){
            return moment(date).format('DD. MMM YYYY.');
        },
    }
};
</script>


<style scoped>

#close_btn{
    height: 30px;
    width: 25px;
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
</style>