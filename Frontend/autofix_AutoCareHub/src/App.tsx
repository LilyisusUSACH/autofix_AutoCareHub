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
              <Route path="/Workshop/*" element={<WorkshopRouter/>} />
              <Route path='/MyVehicle/*' element={<MyVehicleRouter/>}/>
              <Route path="/*" element={<ClientRouter/>}/>
            </Routes>
          </div>
    </Router>
  )
}

export default App
