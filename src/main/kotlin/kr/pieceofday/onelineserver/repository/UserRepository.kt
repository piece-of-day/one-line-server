package kr.pieceofday.onelineserver.repository

import kr.pieceofday.onelineserver.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long>
