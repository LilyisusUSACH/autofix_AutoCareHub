import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import HelloWorld from '../components/HelloWorld';
import HelloWorldOnlyWork from '../components/HelloWorldOnlyWork';

const WorkshopRouter = () => {
    return (
        <div className=''>
            <HelloWorldOnlyWork/>
            <Routes>
                <Route path='/' element={<HelloWorld/>}/>
            </Routes>
        </div>
    );
}

export default WorkshopRouter;