<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <form onsubmit="return false;">     	
                <h4 class="text-center " style="margin-bottom: 40px;">{{title}}</h4>

                <div class="form-group">
                    <label for="price">Complaint</label><br>
                    <textarea disabled class="form-control" v-model="complaint.content"></textarea>
                </div>
                
                <div class="form-group" v-if="disableResponse == false">
                    <label for="price">Response</label><br>
                    <textarea class="form-control" v-model="response" required=""
                        oninvalid="this.setCustomValidity('Enter response.')"  oninput="setCustomValidity('')">
                    </textarea>
                </div>

                <div class="form-group" v-else>
                    <label for="price">Response</label><br>
                    <textarea class="form-control" v-model="complaint.response" disabled></textarea>
                </div>

                <button style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" :hidden="disableResponse"
                    @click="sendResponse(response)">
                    Send Response
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
    name : "AdminComplaintResponseModal",

    components : {Button},

    props : {
        complaint : Object,

        modal_show : Boolean,

        title : {
            type: String,
            default() {
                return "Complaint Response"
            }
        },

        disableResponse: {
            type: Boolean,
            default() {
                return false;
            }
        }
    },
    
    data() {
        return {
            response: '',
        }
    },

    methods : {
        closeWindow : function()
        {
            this.$emit('modal-closed');
        },

        sendResponse : function(response)
        {
            if(response=='') return; 

            this.$emit('send-response-modal', response);
            this.response = '';
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