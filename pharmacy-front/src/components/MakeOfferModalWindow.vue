<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <form onsubmit="return false;">     	
                <h4 class="text-center " style="margin-bottom: 40px;">{{title}}</h4>
                <div class="form-group">
                    <label for="price">Price</label><br>
                    <input type="number" name="price" id="price" class="form-control" v-model="price"
                    required=""
                    oninvalid="this.setCustomValidity('Enter price.')"  oninput="setCustomValidity('')">
                </div>
                <div class="md-form mx-5 my-5">
                    <label for="date">Delivery due date</label>
                    <input type="date" id="date" name="date" v-model="deliveryDueDate" class="form-control"
                    required=""
                    oninvalid="this.setCustomValidity('Enter date.')"  oninput="setCustomValidity('')"/>
                </div>
                <button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md"  
                    @click="sendOffer(order, price, deliveryDueDate)">
                    Send offer
                </button>
            </form>

        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";
import moment from 'moment';


export default {
    name : "MakeOfferModalWindow",

    components : {Button},

    props : {
        order : Object,
        modal_show : Boolean,
        title : {
            type: String,
            default() {
                return "Create offer"
            }
        }
    },
    
    data() {
        return {
            price : '',
            deliveryDueDate: null
        }
    },

    methods : {
        closeWindow : function()
        {
            this.$emit('modal-closed');
        },

        sendOffer : function(order, price, deliveryDueDate)
        {
            if(deliveryDueDate == null) return;
            if(price=='') return; 
            
            if(price < 1)
            {
                alert("Price must be greater than 1");
                return;
            }
            if(moment(deliveryDueDate).isSameOrBefore(order.dueDate)) 
            {
                alert("Delivery date must not be before or same date as offer due date.");
                return;
            }
            
            this.$emit('send-offer-modal', order, price, moment(deliveryDueDate).format('YYYY-MM-DD'));
            this.price = '';
            this.deliveryDueDate = null;
            this.closeWindow();
        },

    },

    mounted() {
        
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
  width: 40%;
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