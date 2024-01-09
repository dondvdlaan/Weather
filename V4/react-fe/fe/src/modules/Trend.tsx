import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import { CityWeather } from '../types/CityWeather'
import { useSchedulerWeatherApi, useWeatherApi } from '../shared/API'

interface Props {
  city: string | undefined
  startTrend: boolean
}
/**
 * Module Trend displays temperatures against sample time in a chart
 * @returns
 */
export const Trend = (props: Props): JSX.Element => {

  // Constants
  //const [weather, setWeather] = useWeatherApi<CityWeather[]>(props.city)
  const [weather, setWeather] = useSchedulerWeatherApi<CityWeather[]>(props.startTrend, props.city)
  const dateTimeFormat = new Intl.DateTimeFormat("de-DE", { dateStyle: 'medium', timeStyle: 'medium' })

  // Wait till weather report arrived
  if (!weather || weather.length < 1) return <p>waiting for weather reports...</p>

  // Variable
  let city: string = "noCity"
  let temps: number[] = []
  let dates: string[] = []

  console.log("weather: ", weather)

  // Preparing data for the x- and y axis
  if (weather.length > 0) {
    city = weather[0].name
    temps = weather.map(w => w.temperature)
    dates = weather.map(w => dateTimeFormat.format(new Date(w.time)))
  }

  // Configure the chart
  const options = {

    title: {
      text: 'Trending',
      align: 'left'
    },

    subtitle: {
      align: 'left'
    },

    yAxis: {
      title: {
        text: 'Degrees Celcius'
      },
      labels: {
        format: '{value}°'
    }
    },

    xAxis: {
      categories: dates,
      accessibility: {
        rangeDescription: 'Sample dates'
      }
    },

    legend: {
      layout: 'vertical',
      align: 'right',
      verticalAlign: 'middle'
    },

    plotOptions: {
      series: {
        label: {
          connectorAllowed: false
        },
        pointStart: 0
      }
    },

    series: [{
      name: city,
      data: temps
    }],

    responsive: {
      rules: [{
        condition: {
          maxWidth: 500
        },
        chartOptions: {
          legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom'
          }
        }
      }]
    }
  }

  return (
    <div>
      <HighchartsReact
        highcharts={Highcharts}
        options={options}
      />
    </div>
  )
}
