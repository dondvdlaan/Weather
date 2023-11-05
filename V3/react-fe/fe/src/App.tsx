import React, { useState } from 'react';
import { Box, FormField, Grommet, Text, TextInput } from 'grommet';
import AppBar from './modules/AppBar'
import SearchCity from './modules/SearchCity';
import { Trend } from './modules/Trend';
import { Timer } from './modules/Timer';


const theme = {
  global: {
    font: {
      family: "Roboto",
      size: "18px",
      height: "20px",
    },
  },
};

function App() {

  //   <SearchCity />

  return (
    <Grommet theme={theme}>
      <AppBar>
        <Text size="large">City Weather App</Text>
      </AppBar>
      <SearchCity />

    </Grommet>
  );
}

export default App;
