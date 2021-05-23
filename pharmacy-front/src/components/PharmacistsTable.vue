<template>
    <div id="pahrmas">
        <label v-if="pharmacists.length == 0"><span style="font-size: 20px;">There are no hired pharmacists.</span></label>
        <div v-if="pharmacists.length != 0">
            <table class="table table-hover"> 
            <!-- v-closable="{exclude: [], handler: 'onOutOfFocus'}"> -->
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Work time</th>
                    </tr>
                </thead>

                <tbody>
                    <tr :key="phar.id" v-for="phar in pharmacists" @click="clickedOnRow(phar)" v-bind:class="{selected : selected_pharmacist.id===phar.id}">
                        <td>{{phar.id}}</td>
                        <td>{{phar.username}}</td>
                        <td>{{phar.email}}</td>
                        <td>{{phar.firstName}}</td>
                        <td>{{phar.lastName}}</td>
                        <td><i v-bind:class="phar.gender != 'MALE' ? 'fa fa-venus': 'fa fa-mars'"></i></td> 
                        <td>
                            <star-rating
                                data-toggle="tooltip" 
                                data-placement="top" 
                                :title="phar.rating"
                                active-color="rgba(155, 82, 151, 0.527)"
                                :inline="true"
                                :star-size="25"
                                :read-only="true"
                                :show-rating="false"
                                :rating="phar.rating"
                                :increment="0.1"
                            ></star-rating>
                        </td>
                        <td>{{phar.workTime.startTime}} - {{phar.workTime.endTime}}</td>
                    </tr>
                </tbody>
            </table>
            <Button
                @action-performed="firePharmacist()"
                class="btn" 
                text="Fire pharmacist" 
                bgd_color="rgba(15, 95, 72, 0.85)" 
                :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'left'}">
            </Button>
        </div>
        <ModalWindowHirePharmacist
            @registeredPharmacist = "addPharmacistIntoList"
            @modal-closed = "modal_window_show_pharmacist_reg = false" 
            :modal_show = "modal_window_show_pharmacist_reg"
            :pharmacyId = "pharmacyId"
        >
        </ModalWindowHirePharmacist>
        <Button
            @action-performed="hirePharmacist()"
            class="btn" 
            text="Hire pharmacist" 
            bgd_color="rgba(15, 95, 72, 0.85)" 
            :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'right'}">
        </Button>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from './Button.vue';
import ModalWindowHirePharmacist from './ModalWindowHirePharmacist.vue';
import StarRating from 'vue-star-rating';

export default {
    name: "PharmacistsTable",
    components: { Button, ModalWindowHirePharmacist, StarRating},
    props: {
        pharmacists : {
            type : Array,
            default() {
                return [];
            }
        },
        pharmacyId : {
            type : Number,
            default() {
                return -1;
            }
        }
    },
    data() {
        return {
            modal_window_show_pharmacist_reg : false,
            selected_pharmacist: { id : -1},
        }
    },
    methods: {
        hirePharmacist: function(id){
            this.modal_window_show_pharmacist_reg = true;
        },
        clickedOnRow: function(phar){
            this.selected_pharmacist = phar;
        },
        onOutOfFocus: function()
        {
            this.selected_pharmacist = { id : -1};
        },
        addPharmacistIntoList: function(pharmacist){
            this.modal_window_show_pharmacist_reg = false;
            this.$emit('registeredPharmacist', pharmacist);
        },
        firePharmacist : function(){
            if(this.selected_pharmacist.id != -1){
                client({
                    url: "employments/" + this.selected_pharmacist.employmentId,
                    method: "DELETE"
                }).then((response) => {
                    this.$emit('fired-pharmacist', this.selected_pharmacist);
                });
            }
            else
                this.$toasted.show("Please select pharmacist to fire!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
        }
    }
}

</script>

<style scoped>
#pahrmas {
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
