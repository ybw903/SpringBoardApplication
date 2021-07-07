import Gnb from "./GnB"
export default function Top() {
    return (
        <div>
            <div className="jumbotron text-center mb-0">
                <h1>My board</h1>
                <p>게시글을 작성하고 확인하세요</p>
            </div>
            <Gnb/>
        </div>

    )
}