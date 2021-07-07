import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'

export default function Home() {

  const posts = [
    {'id':0, 'title': '안녕', 'content': '내용 어쩌구 저쩌구', 'author': '작성자'},
    {'id':1, 'title': '안녕1', 'content': '내용 저쩌구', 'author': '작성자'},
    {'id':2, 'title': '안녕2', 'content': '내용 어쩌구', 'author': '작성자'},
    {'id':3, 'title': '안녕3', 'content': '내용 어쩌구 저쩌구', 'author': '작성자'},
    {'id':4, 'title': '안녕', 'content': '내용 저쩌구', 'author': '작성자'},
    {'id':5, 'title': '안녕4', 'content': '내용 어쩌구', 'author': '작성자'},
    {'id':6, 'title': '안녕', 'content': '내용 저쩌구', 'author': '작성자'},
    {'id':7, 'title': '안녕5', 'content': '내용 어쩌구', 'author': '작성자'},
    {'id':8, 'title': '안녕', 'content': '내용 저쩌구', 'author': '작성자'},
    {'id':9, 'title': '안녕', 'content': '내용 어쩌구', 'author': '작성자'},
    
  ] 

  return (
    <div>
      <Head>
        <title>Home | MyBoard</title>
      </Head>
      <table className ="table table-striped">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>내용</th>
            <th>작성자</th>
          </tr>
        </thead>
        <tbody>
          {posts.map((post)=> 
            <tr key={post.id}>
              <td>{post.id}</td>
              <td>{post.title}</td>
              <td>{post.content}</td>
              <td>{post.author}</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}
