package kr.pieceofday.onelineserver.auth

import kr.pieceofday.onelineserver.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val id = userId.toLong()
        val user = userRepository.findById(id).orElseThrow {
            UsernameNotFoundException("Not found User")
        }
        return user
    }
}
