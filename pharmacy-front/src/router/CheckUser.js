import {client} from '@/client/axiosClient'

export function isUserLoggedIn(){
    var userData = getLoggedUserData();
    //If user token present, user is logged.
    if(userData['USER_TOKEN']) return true;

    return false;
}

export function getLoggedUserData(){
    return {
      userType: _getUserType(),
      userToken: _getUserToken(),
      userExpires: _getUserExpires()
    }
}

export function _getUserType(){    
    return localStorage.getItem('USER_TYPE');;
  }

export function _getUserToken(){
    return localStorage.getItem('USER_TOKEN');
}

export function _getUserExpires(){
    return localStorage.getItem('USER_EXPIRES');
}
