
import SignUpForm from "./components/SignUpForm";
import SignInForm from "./components/SignInForm";
import ContentRoot from "./components/ContentRoot";

import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";


function App() {
  return (
    <div className="App gradient-custom">
        <Router>
            <Routes>
                <Route exact path="/" element={<Link to="/signin">Sign In</Link>}/>
                <Route path="/signin" element={<SignInForm />}/>
                <Route path="/signup" element={<SignUpForm />}/>
                <Route path="*" element={<h2>404</h2>}/>
            </Routes>
        </Router>
    </div>
  );
}

export default App;
