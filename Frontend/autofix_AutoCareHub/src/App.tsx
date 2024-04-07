import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import './App.css'
import WorkshopRouter from './routers/WorkshopRouter'
import ClientRouter from './routers/ClientRouter'
import MyVehicleRouter from './routers/MyVehicleRouter'

function App() {

  return (
    <Router>
          <div>
            <Routes>
              <Route path="/*" element={<ClientRouter/>}/>
              <Route path='/MyVehicle/*' element={<MyVehicleRouter/>}/>
              <Route path="/workshop/*" element={<WorkshopRouter/>} />
            </Routes>
          </div>
    </Router>
  )
}

export default App
