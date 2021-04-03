<template>
    <div id="dermos">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First name</th>
                    <th scope="col">Last name</th>
                    <th scope="col">Rating</th>
                    <th scope="col">Appointment</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="der.id" v-for="der in dermatologists">
                    <td>{{der.id}}</td>
                    <td>{{der.firstName }}</td>
                    <td>{{der.lastName}}</td> 
                    <td>{{der.rating}}</td>
                    <td><Button class="btn-success" text="Create" color="green"></Button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import Button from './Button.vue';

export default {
    name: "Dermatologists",
    components: { Button },
    props: {
        dermatologists : {
            type : Array,
            default() {
                return [];
            }
        }
    },
    data() {
        return {
        }
    }
}

</script>

<style scoped>
#dermos {
    margin: 30px 10px 10px 10px;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>
