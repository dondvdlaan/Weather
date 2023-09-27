import { Box, FormField, TextInput } from "grommet"
import { useState } from "react";
import { CityAPI } from "../shared/API";
import { CityWeather } from "../types/CityWeather";
import { WeatherDetails } from "./WeatherDetails";

/**
 * This module recieves search input from user looking for a city weather report and communicates with
 * backend.
 * @returns 
 */
const SearchCity = () =>{
  
  // ---- Constants and variables ----
  const [searchItem, setSearchItem]       = useState<string | undefined>("")
  const [searchResults, setSearchResults] = useState<CityWeather>();
 
  // ---- Events ----
  const onChange = (c: React.ChangeEvent<HTMLInputElement>) => {

    const inputValue = c.target.value;
    console.log("inputValue: ", inputValue)
    setSearchItem(inputValue)
    
    CityAPI(inputValue)
    .then(res=> {
      console.log("cityWeather: ", res.data)
      setSearchResults(res.data)
    })
    .catch(err=> console.log("err: ", err))
    // TODO proper error message to be sent to page for user info
  }

  const onClick = () => {

    console.log("clickValue: ", searchResults?.name)
    
    setSearchItem(searchResults?.name)
  }

    return(
      <>
      <Box fill align="center" justify="start" pad="large">
        <Box width="medium">

        <TextInput 
          placeholder="type here" 
          id="text-input"
          value={searchItem}
          onChange={c => onChange(c)}
        />
        <span
          onClick={onClick}
        >
          <p> City: {searchResults?.name} </p>
        </span>
        </Box>
      </Box>
      <WeatherDetails cityWeather={searchResults }/>
      </>
    )
}

export default SearchCity;