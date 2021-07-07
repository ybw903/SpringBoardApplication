export default function Gnb() {
    return (
        <nav className="navbar navbar-expand-sm navbar-dark bg-dark">
            <a href="#" className="navbar-brand">My Board</a> 
             
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
                <span className="navbar-toggler-icon"></span> 
            </button> 
            <div className="collapse navbar-collapse" id="collapsibleNavbar"> 
            <ul className="navbar-nav">
                 <li className="nav-item"><a href="#" className="nav-link">홈</a></li> 
                 <li className="nav-item"><a href="#" className="nav-link">게시판</a></li>
                <li className="nav-item"><a href="#" className="nav-link">알수없음</a></li>
            </ul> 
            </div> 
        </nav>

    );
}