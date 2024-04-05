import { useState } from 'react'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import './App.css'
import HelloWorld from './components/HelloWorld'
import WorkshopRouter from './routers/WorkshopRouter'
import ClientRouter from './routers/ClientRouter'

function App() {

  return (
    <Router>
          <div>
            <Routes>
              <Route path="/" element={<ClientRouter/>}/>
              <Route path="/workshop/*" element={<WorkshopRouter/>} />
              <Route path="*" element={<HelloWorld/>} />
            </Routes>
          </div>
    </Router>
  )
}

export default App
