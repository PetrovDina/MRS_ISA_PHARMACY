<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <ul id="test-test">
      <li v-for="med in medication">
        {{ med.id }} - {{med.medicationName}} 
      </li>
    </ul>
    <button v-on:click='test()'>Test</button>
  </div>
</template>

<script>
import axios from 'axios'; //todo obrisati odavde jer se ovako ne radi, moracemo da pravimo axios clienta kao ovi 
export default {
  name: 'TestComponent',
  data () {
    return {
      msg: 'Here we can test our dummy data',
      medication: []
    }
  },

  methods: {
  test: function () {
        //ovde ce biti neki poziv ka bekendu da vidimo da li uspevamo da dobavimo podatke o nekom leku
        // axios
        //     .get('https://api.coindesk.com/v1/bpi/currentprice.json') //test
        //     .then(response => (this.msg = response))
        //     }
        axios
             .get('http://localhost:8080/med/test') //test
             .then(response => (this.medication= response.data))
             }
}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
