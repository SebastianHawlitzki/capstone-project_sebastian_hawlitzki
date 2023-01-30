import {useEffect, useState} from "react";
import {getLoggedInUser} from "../api/Api";
import {AppUser} from "../models/AppUser";


export default function useAppUser(){
     const [appUser, setAppUser] = useState<AppUser>();

     useEffect(() => {
          (async () => {
               const appUser = await getLoggedInUser();
               setAppUser(appUser)
          })();
     }, []);

     return {appUser};
}