import { Grommet, Text } from 'grommet';
import AppBar from './AppBar'
import { TestHttps } from './TestHttps';
import SearchCity from './SearchCity';


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


  //<TestHttps />
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
